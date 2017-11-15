package com.digitalsolutionsexpert.CustomerNotification.Service.PollingController;

import com.digitalsolutionsexpert.CustomerNotification.Application.ApplicationConfiguration;
import com.digitalsolutionsexpert.CustomerNotification.Application.ConfigurationProperties;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseServiceException;
import com.digitalsolutionsexpert.CustomerNotification.Service.BaseThreadedService;
import com.digitalsolutionsexpert.CustomerNotification.Service.PollingController.Persistence.BasePollingPersistenceService;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.*;

public class BasePollingControllerService extends BaseThreadedService {
    private final BlockingQueue<Runnable> blockingQueue;
    private int blockingQueueMaxSize;
    private final BasePollingPersistenceService persistenceService;
    private final BasePoolingProducerService producerService;
    private ThreadPoolExecutor threadPoolExecutor;
    private RejectedExecutionHandler rejectedExecutionHandler;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    private TimeUnit unit;

    public BasePollingControllerService(ApplicationConfiguration applicationConfiguration, String path, String name) throws BaseServiceException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(applicationConfiguration, path, name);
        this.setBlockingQueueMaxSize(Integer.valueOf(String.valueOf(this.getProperties().get("blocking-queue-max-size"))));
        this.blockingQueue = new LinkedBlockingQueue(this.getBlockingQueueMaxSize());
        this.persistenceService = (BasePollingPersistenceService) this.getApplicationConfiguration().getService(path + "." + name + "." + "service" + "." + "service-polling-persistence");
        this.producerService = (BasePoolingProducerService) this.getApplicationConfiguration().getService(path + "." + name + "." + "service" + "." + "service-polling-producer");
        this.producerService.setPollingControllerService(this);

        ConfigurationProperties configurationPropertiesThreadPoolExecutor = this.getProperties().getConfigurationSubtree("thread-pool-executor");
        this.corePoolSize = Integer.valueOf(String.valueOf(configurationPropertiesThreadPoolExecutor.get("core-pool-size", "1")));
        this.maximumPoolSize = Integer.valueOf(String.valueOf(configurationPropertiesThreadPoolExecutor.get("maximum-pool-size", "1")));
        this.keepAliveTime = Integer.valueOf(String.valueOf(configurationPropertiesThreadPoolExecutor.get("keep-alive-time", "1000")));
        this.unit = TimeUnit.valueOf(String.valueOf(configurationPropertiesThreadPoolExecutor.get("time-unit", "MILLISECONDS")));

        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        this.rejectedExecutionHandler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executor.execute(r);
            }
        };
        this.threadPoolExecutor = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize, this.keepAliveTime, this.unit, this.blockingQueue, threadFactory, rejectedExecutionHandler);
        this.requestStart();
    }

    public BlockingQueue<Runnable> getBlockingQueue() {
        return blockingQueue;
    }

    public int getBlockingQueueMaxSize() {
        return blockingQueueMaxSize;
    }

    private void setBlockingQueueMaxSize(int blockingQueueMaxSize) {
        this.blockingQueueMaxSize = blockingQueueMaxSize;
    }

    public int getBlockingQueueFreeSize() {
        return this.getBlockingQueueMaxSize() - this.getBlockingQueue().size();
    }

    public BasePollingPersistenceService getPersistenceService() {
        return persistenceService;
    }

    public BasePoolingProducerService getProducerService() {
        return producerService;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }


}

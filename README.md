# ClassLoaderTest

# 总结
## 1. 和上下文类加载器相关的调用

① SPI 发现 Impl 的时候调用 load 方法不传类加载器
```java
    public static <S> ServiceLoader<S> load(Class<S> service) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return ServiceLoader.load(service, cl);
    }
```

② 反射调用
```java
// Flink 代码精简截取
Classlaoer cl = userClassloader; 

className = "com.dtstack.flinkx.Main"
Thread.currentThread().setContextClassLoader(cl);
Class<?> entryClass = Class.forName(className, false, cl);
Method mainMethod = entryClass.getMethod("main", String[].class);
mainMethod.invoke(null, (Object) args);

```




HDFS FileSystem 采用的就是 SPI 机制
```java

private static FileSystem createFileSystem(URI uri, Configuration conf) throws IOException {
    Class<?> clazz = getFileSystemClass(uri.getScheme(), conf); // getFileSystemClass 方法
    FileSystem fs = (FileSystem)ReflectionUtils.newInstance(clazz, conf);
    fs.initialize(uri, conf);
    return fs;
}

public static Class<? extends FileSystem> getFileSystemClass(String scheme, Configuration conf) throws IOException {
    if (!FILE_SYSTEMS_LOADED) {
        loadFileSystems();  // loadFileSystems 方法
    }
    Class<? extends FileSystem> clazz = null;
    if (conf != null) {
        clazz = (Class<? extends FileSystem>) conf.getClass("fs." + scheme + ".impl", null);
    }
    if (clazz == null) {
        clazz = SERVICE_FILE_SYSTEMS.get(scheme);
    }
    if (clazz == null) {
        throw new IOException("No FileSystem for scheme: " + scheme);
    }
    return clazz;
}

private static void loadFileSystems() {
    synchronized (FileSystem.class) {
        if (!FILE_SYSTEMS_LOADED) {
            ServiceLoader<FileSystem> serviceLoader = ServiceLoader.load(FileSystem.class); // SPI 方法
            Iterator<FileSystem> it = serviceLoader.iterator();
            while (it.hasNext()) {
                FileSystem fs = null;
                try {
                    fs = it.next();
                    try {
                        SERVICE_FILE_SYSTEMS.put(fs.getScheme(), fs.getClass());
                    } catch (Exception e) {
                        LOG.warn(
                        "Cannot load: "
                        + fs
                        + " from "
                        + ClassUtil.findContainingJar(fs.getClass()),
                        e);
                    }
                } catch (ServiceConfigurationError ee) {
                    LOG.warn("Cannot load filesystem", ee);
                }
            }
            FILE_SYSTEMS_LOADED = true;
        }
    }
}
```




package com.gitee.starblues.extension.mybatis;

import com.gitee.starblues.extension.mybatis.utils.MybatisXmlProcess;
import com.gitee.starblues.factory.PluginRegistryInfo;
import com.gitee.starblues.factory.process.pipe.PluginPipeProcessorExtend;
import com.gitee.starblues.loader.PluginResourceLoadFactory;
import com.gitee.starblues.loader.ResourceWrapper;
import com.gitee.starblues.realize.BasePlugin;
import com.gitee.starblues.utils.OrderPriority;
import org.apache.ibatis.session.SqlSessionFactory;
import org.pf4j.PluginWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * mybatis xml 处理者
 *
 * @author zhangzhuo
 * @version 2.2.0
 */
public class PluginMybatisXmlProcessor implements PluginPipeProcessorExtend {

    private final MybatisXmlProcess mybatisXmlProcess;
    private final SqlSessionFactory sqlSessionFactory;

    PluginMybatisXmlProcessor(ApplicationContext mainApplicationContext) {
        sqlSessionFactory = mainApplicationContext.getBean(SqlSessionFactory.class);
        if(sqlSessionFactory != null){
            this.mybatisXmlProcess = MybatisXmlProcess.getInstance(sqlSessionFactory);
        } else {
            this.mybatisXmlProcess = null;
        }
    }

    @Override
    public String key() {
        return "PluginMybatisXmlProcessor";
    }

    @Override
    public OrderPriority order() {
        return OrderPriority.getLowPriority();
    }

    @Override
    public void initialize() throws Exception {

    }

    @Override
    public void registry(PluginRegistryInfo pluginRegistryInfo) throws Exception {
        if(mybatisXmlProcess == null || sqlSessionFactory == null){
            return;
        }
        BasePlugin basePlugin = pluginRegistryInfo.getBasePlugin();
        PluginWrapper pluginWrapper = pluginRegistryInfo.getPluginWrapper();
        PluginResourceLoadFactory pluginResourceLoadFactory = basePlugin.getBasePluginExtend().getPluginResourceLoadFactory();


        ResourceWrapper resourceWrapper = pluginResourceLoadFactory.getPluginResources(PluginMybatisXmlLoader.KEY);
        if(resourceWrapper == null){
            return;
        }
        processAliases(pluginRegistryInfo);
        List<Resource> pluginResources = resourceWrapper.getResources();
        if(pluginResources == null || pluginResources.isEmpty()){
            return;
        }
        mybatisXmlProcess.loadXmlResource(
                pluginRegistryInfo.getPluginWrapper().getPluginId(),
                pluginResources, pluginWrapper.getPluginClassLoader());
    }

    private void processAliases(PluginRegistryInfo pluginRegistryInfo) {

    }

    @Override
    public void unRegistry(PluginRegistryInfo pluginRegistryInfo) throws Exception {
        mybatisXmlProcess.unRegistry(pluginRegistryInfo.getPluginWrapper().getPluginId());
    }

}

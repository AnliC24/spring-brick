package com.gitee.starblues.extension.mybatis;

import com.gitee.starblues.extension.AbstractExtension;
import com.gitee.starblues.factory.process.pipe.PluginPreProcessorExtend;
import com.gitee.starblues.loader.PluginResourceLoader;
import com.gitee.starblues.factory.process.pipe.PluginPipeProcessorExtend;
import com.gitee.starblues.factory.process.pipe.classs.PluginClassGroupExtend;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * spring boot mybatis 扩展
 *
 * @author zhangzhuo
 * @version 1.0
 */
public class SpringBootMybatisExtension extends AbstractExtension {

    private static final String KEY = "SpringBootMybatisExtension";


    @Override
    public String key() {
        return KEY;
    }



    @Override
    public List<PluginResourceLoader> getPluginResourceLoader() {
        final List<PluginResourceLoader> pluginResourceLoaders = new ArrayList<>();
        pluginResourceLoaders.add(new PluginMybatisXmlLoader());
        return pluginResourceLoaders;
    }

    @Override
    public List<PluginClassGroupExtend> getPluginClassGroup(ApplicationContext mainApplicationContext) {
        final List<PluginClassGroupExtend> pluginClassGroups = new ArrayList<>();
        pluginClassGroups.add(new PluginMapperGroup());
        pluginClassGroups.add(new PluginEntityAliasesGroup());
        return pluginClassGroups;
    }

    @Override
    public List<PluginPipeProcessorExtend> getPluginPipeProcessor(ApplicationContext applicationContext) {
        final List<PluginPipeProcessorExtend> pluginPipeProcessorExtends = new ArrayList<>();
        pluginPipeProcessorExtends.add(new PluginMybatisXmlProcessor(applicationContext));
        pluginPipeProcessorExtends.add(new PluginMybatisEntityProcessor(applicationContext));
        return pluginPipeProcessorExtends;
    }

    @Override
    public List<PluginPreProcessorExtend> getPluginPreProcessor(ApplicationContext applicationContext) {
        final List<PluginPreProcessorExtend> pluginPreProcessorExtends = new ArrayList<>();
        pluginPreProcessorExtends.add(new PluginMybatisMapperProcessor(applicationContext));
        return pluginPreProcessorExtends;
    }


}

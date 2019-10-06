
package config;

import filter.CORSFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.Filter;

public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getServletConfigClasess() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMapping() {
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CORSFilter()};
    }


    }


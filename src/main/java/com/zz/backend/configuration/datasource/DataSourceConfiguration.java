package com.zz.backend.configuration.datasource;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.zz.backend.util.RunningProfile;

//@Configuration
//@Profile(RunningProfile.Production)
public class DataSourceConfiguration {

	public DataSource getDatasource(){
	
		return null;
	}
}

package jcox.security.rcp.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jcox.security.rcp.api.DateService;

@Service
public class RealDateService implements DateService {

	private final static Logger logger = LoggerFactory.getLogger(RealDateService.class);
	
	@Override
	public Date getDate() {
		
		logger.debug("getDate()");
		
		return new Date();
	}

}

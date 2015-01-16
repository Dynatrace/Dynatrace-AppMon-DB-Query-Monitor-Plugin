package com.db.monitor;

import com.dynatrace.diagnostics.pdk.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Collection;


public class QMonitor extends QueryMonitor implements Monitor {

	private static final Logger log = Logger.getLogger(QMonitor.class.getName());
	private static final String METRIC_GROUP = "Query Monitor";
	private static final String MSR_ROWS = "Row Count";
	private static final String MSR_CONNECT = "DB Connection";
	private static final String MSR_VALUE = "Column Value";
	private static final String MSR_RESPONSETIME = "Response Time";
	private static final String MSR_CONNECTRESPONSETIME = "Connect Response Time";
	private static final String MSR_QUERYRESPONSETIME = "Query Response Time";
	private static final String MSR_CONTENTVERIFIED = "Content Verified";
	private static final String CONFIG_QNAME = "QueryName";

	@Override
	public Status setup(MonitorEnvironment env) throws Exception {
		// TODO
		return super.setup(env);
	}

	@Override
	public Status execute(MonitorEnvironment env) throws Exception {
		Status result = super.execute(env);
		MonitorMeasure dynamicMeasure;

		Collection<MonitorMeasure> measures;
			
		if ((measures = env.getMonitorMeasures(METRIC_GROUP, MSR_ROWS)) != null) {
			for (MonitorMeasure measure : measures) {
				measure.setValue(numRows());
			}
		}
		
		if ((measures = env.getMonitorMeasures(METRIC_GROUP, MSR_CONNECT)) != null) {
			for (MonitorMeasure measure : measures) {
				measure.setValue(numConnect());
			}
		}
		
		if ((measures = env.getMonitorMeasures(METRIC_GROUP, MSR_CONTENTVERIFIED)) != null) {
			for (MonitorMeasure measure : measures) {
				measure.setValue(contentVerified());
			}
		}
		
		if ((measures = env.getMonitorMeasures(METRIC_GROUP, MSR_RESPONSETIME)) != null) {
			for (MonitorMeasure measure : measures) {
				measure.setValue(responseTime());
			}
		}
		
		if ((measures = env.getMonitorMeasures(METRIC_GROUP, MSR_CONNECTRESPONSETIME)) != null) {
			for (MonitorMeasure measure : measures) {
				measure.setValue(connectResponseTime());
			}
		}
		
		if ((measures = env.getMonitorMeasures(METRIC_GROUP, MSR_QUERYRESPONSETIME)) != null) {
			for (MonitorMeasure measure : measures) {
				measure.setValue(queryResponseTime());
			}
		}
		
		if ((measures = env.getMonitorMeasures(METRIC_GROUP, MSR_VALUE)) != null) {
			double cValues[] = cValue();	
			String measureNames[] = getMeasureNames();
			String queryName = env.getConfigString(CONFIG_QNAME);

			/*
			 * Add in a check to see if the plug-in should be backward compatible with the 2.0 version, which doesn't use dynamic measures.
			 * Only turn on dynamic measures if a query name is specified or there are multiple column names specified. 
			 */
			boolean useDynamicMeasures = true;
			if (queryName == null || queryName.trim().length() == 0) {
				queryName = "Query";
				useDynamicMeasures = false;
				
				if (measureNames != null && measureNames.length>1 && measureNames[1] != null && measureNames[1].trim().length()>0)
					useDynamicMeasures=true;
			}
			
			if (cValues != null && measureNames != null) {
				for (MonitorMeasure measure : measures) {
					if (useDynamicMeasures) {
						int i = 0;
						for (String measureName : measureNames) {
							if (measureName != null && measureName.trim().length() > 0) {
								
								if (log.isLoggable(Level.FINE))
									log.fine("Setting dynamic measure " + queryName + "->" + measureNames[i] + ", value "+ cValues[i]);
								
								dynamicMeasure = env.createDynamicMeasure(measure, queryName, measureNames[i]);
								dynamicMeasure.setValue(cValues[i]);
							}
							i++;
						}
					}
					
					/*
					 * The first value returned is going to be the default 'column value' measure used.
					 * We conditionally are setting this value because the dynamic measure matrix otherwise
					 * shows this value, which looks a bit strange.
					 */
					if (log.isLoggable(Level.FINE))
						log.fine("Setting DB Query " + MSR_VALUE + " measure with value "+ cValues[0]);
					measure.setValue(cValues[0]);
				}
			}
		}

		return result;
	}

	
	@Override
	public void teardown(MonitorEnvironment env) throws Exception {
		// TODO
		 super.teardown(env);
	}
}

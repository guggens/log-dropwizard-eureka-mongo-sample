package com.guggens.log.producer.client;

import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.jetty.HttpsConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.server.ServerFactory;
import io.dropwizard.server.SimpleServerFactory;

class DropwizardServerHelpers {
   public static Integer getPort(ServerFactory serverFactory) {
      if(serverFactory instanceof SimpleServerFactory) {
         return getPort(((SimpleServerFactory)serverFactory).getConnector());
      }
      else if(serverFactory instanceof DefaultServerFactory) {
         return getPort(((DefaultServerFactory)serverFactory).getApplicationConnectors().get(0));
      }
      throw new RuntimeException("Unable to infer Port of " + serverFactory);
   }

   public static Integer getAdminPort(ServerFactory serverFactory) {
      if(serverFactory instanceof SimpleServerFactory) {
         return getPort(((SimpleServerFactory)serverFactory).getConnector());
      }
      else if(serverFactory instanceof DefaultServerFactory) {
         return getPort(((DefaultServerFactory)serverFactory).getAdminConnectors().get(0));
      }
      throw new RuntimeException("Unable to infer AdminPort of " + serverFactory);
   }

   public static Integer getPort(ConnectorFactory connectorFactory) {
      if(connectorFactory instanceof HttpConnectorFactory) {
         return ((HttpConnectorFactory)connectorFactory).getPort();
      }
      if(connectorFactory instanceof HttpsConnectorFactory) {
         return ((HttpsConnectorFactory)connectorFactory).getPort();
      }
      throw new RuntimeException("Unable to infer Port of " + connectorFactory);
   }
}

package com.guggens.log.writer.client;

import com.google.common.collect.Lists;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * Stolen from ServiceInstanceBuilder in Curator.
 */
public class LocalIpAddress {
   private static boolean use(NetworkInterface nif, InetAddress adr) throws SocketException {
      return (adr != null) && !adr.isLoopbackAddress() && (nif.isPointToPoint() || !adr.isLinkLocalAddress());
   }

   public static Collection<InetAddress> getAllLocalIPs() {
      try {
         List<InetAddress> listAdr = Lists.newArrayList();
         Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
         if(nifs == null)
            return listAdr;

         while(nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();
            // We ignore subinterfaces - as not yet needed.
            Enumeration<InetAddress> adrs = nif.getInetAddresses();
            while(adrs.hasMoreElements()) {
               InetAddress adr = adrs.nextElement();
               if(use(nif, adr)) {
                  listAdr.add(adr);
               }
            }
         }
         return listAdr;
      }
      catch(SocketException e) {
         throw new RuntimeException(e);
      }
   }

   public static InetAddress guessLocalIp() {
      Collection<InetAddress> localIps = getAllLocalIPs();
      if(localIps.isEmpty()) {
         throw new RuntimeException("No local IPs found");
      }
      return localIps.iterator().next();
   }
}

package com.guggens.log.writer.client;

import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

public class DownTask extends Task {
   private EurekaInstance eurekaInstance;

   public DownTask(EurekaInstance eurekaInstance) {
      super("down");
      this.eurekaInstance = eurekaInstance;
   }

   @Override
   public void execute(ImmutableMultimap<String, String> parameters, PrintWriter writer) throws Exception {
      eurekaInstance.markAsDown();
      writer.write("DOWN");
   }
}

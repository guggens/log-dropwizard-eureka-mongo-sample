package com.guggens.log.producer.client;

import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

public class UpTask extends Task {
   private EurekaInstance eurekaInstance;

   public UpTask(EurekaInstance eurekaInstance) {
      super("up");
      this.eurekaInstance = eurekaInstance;
   }

   @Override
   public void execute(ImmutableMultimap<String, String> parameters, PrintWriter writer) throws Exception {
      eurekaInstance.markAsUp();
      writer.write("UP");
   }
}

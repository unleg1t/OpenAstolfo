package astolfo.api.auth.handler;

import astolfo.api.auth.net.PacketOutput;
import astolfo.api.auth.packet.PacketId;
import astolfo.modules.impl.exploits.Disabler;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class DisablerScriptHandler extends PacketHandler<String> {
   public DisablerScriptHandler() {
      super(PacketId.DISABLER_SCRIPT);
   }

   public void handle(String string, PacketOutput packetOutput) {
      ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
      ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
      scriptEngine.put("disabler", new DisablerScriptSender(this, packetOutput));

      try {
         Disabler.jsHandler = scriptEngine.eval(string);
      } catch (ScriptException scriptException) {
         scriptException.printStackTrace();
      }
   }
}

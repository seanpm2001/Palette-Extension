import org.nlogo.agent.Turtle;
import org.nlogo.agent.Link;
import org.nlogo.agent.Patch;
import org.nlogo.core.AgentKind;
import org.nlogo.core.AgentKindJ;
import org.nlogo.api.AgentException;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.Command;
import org.nlogo.core.LogoList;
import org.nlogo.api.LogoListBuilder;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.Agent;

public class SetAgentAlpha implements Command {
  public Syntax getSyntax() {
    int values[] = {Syntax.NumberType()};
    return SyntaxJ.commandSyntax(values, "-TPL");
  }

  public void perform(Argument args[], Context context) throws ExtensionException {
    double alpha = 255;
    try {
      alpha = args[0].getDoubleValue();
    }
    catch(ExtensionException e) {
      throw new ExtensionException(e.getMessage());
    }
    if(context.getAgent().kind() == AgentKindJ.Patch() && !org.nlogo.api.Version$.MODULE$.is3D()) {
      throw new ExtensionException("Alpha for 2D patches cannot be changed.");
    }
    if(alpha < 0 || alpha > 255) {
      throw new ExtensionException("Alpha must be in the range from 0 to 255.");
    }
    setColor(context, alpha);
  }

  public void setColor(Context context, double alpha) throws ExtensionException {
    ColorManager colorManager = new ColorManager();
    LogoList rgb = colorManager.getAgentColor(context);
    LogoListBuilder newColor = new LogoListBuilder();
    for(int i = 0; i < 3; i++) {
      newColor.add(rgb.get(i));
    }
    newColor.add(alpha);
    try {
      colorManager.setAgentColor(context, newColor.toLogoList());
    }
    catch(ExtensionException e) {
      throw new ExtensionException(e.getMessage());
    }
  }

}

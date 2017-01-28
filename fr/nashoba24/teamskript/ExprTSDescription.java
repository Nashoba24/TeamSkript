package fr.nashoba24.teamskript;

import java.util.HashMap;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.github.theholywaffle.teamspeak3.api.ClientProperty;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprTSDescription extends SimpleExpression<String>{
	private Expression<Client> client;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		client = (Expression<Client>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "ts3 description";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(TeamSkript.ts3api==null || client.getSingle(e)==null) { return null; }
		return new String[]{ client.getSingle(e).get(ClientProperty.CLIENT_DESCRIPTION) };
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if(TeamSkript.ts3api==null || client.getSingle(e)==null) { return; }
			HashMap<ClientProperty, String> map = new HashMap<ClientProperty, String>();
			map.put(ClientProperty.CLIENT_DESCRIPTION, (String) delta[0]);
			TeamSkript.ts3api.editClient(client.getSingle(e).getId(), map);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}
}


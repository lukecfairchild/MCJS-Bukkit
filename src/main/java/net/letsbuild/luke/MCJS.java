package net.letsbuild.luke;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MCJS extends JavaPlugin {
	
	// Fired when MCJS is first enabled
	@Override
	public void onEnable() {

		/**
		 * Initialize Javascript Engine.
		 */
		javax.script.ScriptEngineManager scriptManager = new javax.script.ScriptEngineManager();
		javax.script.ScriptEngine        jsEngine      = scriptManager.getEngineByName( "javascript" );


		/**
		 * Figure out the current Directory.
		 */

		String serverDir  = java.lang.System.getProperty( "user.dir" );
		String pluginDir  = Paths.get( serverDir, "plugins", "MCJS" ).toString();
		String jsFilePath = Paths.get( pluginDir, "lib", "global.js" ).toString();


		/**
		 * Read global.js File.
		 */

		File file                  = new File( jsFilePath );
		FileInputStream fileStream = new FileInputStream( file );
		byte[] data                = new byte[ ( int ) file.length() ];

		fileStream.read( data );
		fileStream.close();

		String javascript = new String( data, "UTF-8" );


		/**
		 * Pass required variables to Javascript Engine.
		 */

		jsEngine.put( "PATH", pluginDir );
		jsEngine.eval( "var global = {};" );
		jsEngine.eval( javascript );
	}

	// Fired when SpigotPlugin is disabled
	@Override
	public void onDisable() {}
}

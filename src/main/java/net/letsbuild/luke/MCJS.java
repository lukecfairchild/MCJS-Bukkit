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
	
	public static Plugin plugin;
    public static final int id = 0;
    
	// Fired when SpigotPlugin is first enabled
	@Override
	public void onEnable() {
		plugin = this;

		/**
		 * Initialize Javascript Engine.
		 */
		ScriptEngineManager scriptManager = new ScriptEngineManager();
		ScriptEngine        jsEngine      = scriptManager.getEngineByName( "javascript" );


		/**
		 * Figure out the current Directory.
		 */

		String serverDir  = System.getProperty( "user.dir" );
		String pluginsDir = Paths.get( serverDir, "plugins" ).toString();
		String pluginDir  = Paths.get( pluginsDir, "MCJS" ).toString();
		String jsFilePath = Paths.get( pluginDir, "lib", "global.js" ).toString();


		/**
		 * Read global.js File.
		 */

		File file   = new File( jsFilePath );
		byte[] data = new byte[ ( int ) file.length() ];
		
		FileInputStream fileStream;

		try {
			fileStream = new FileInputStream( file );

			fileStream.read( data );
			fileStream.close();

		} catch ( FileNotFoundException e ) {
			e.printStackTrace();

		} catch ( IOException e ) {
			e.printStackTrace();
		}

		try {
			String javascript = new String( data, "UTF-8" );


			/**
			 * Pass required variables to Javascript Engine.
			 */

			jsEngine.put( "PATH", pluginDir );
			jsEngine.eval( "var global = {};" );
			jsEngine.eval( javascript );
			
		} catch ( UnsupportedEncodingException e ) {
			e.printStackTrace();

		} catch ( ScriptException e ) {
			e.printStackTrace();
		}
	}


	// Fired when SpigotPlugin is disabled
	@Override
	public void onDisable() {
		
	}
}

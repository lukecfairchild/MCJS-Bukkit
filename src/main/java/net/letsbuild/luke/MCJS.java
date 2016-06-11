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

	public static Plugin              plugin;

	private static ScriptEngine        jsEngine;
	private static ScriptEngineManager scriptManager;

	@Override
	public void onEnable () {

		plugin = this;


		/**
		 * Initialize Javascript Engine.
		 */

		if ( scriptManager == null ) {
			scriptManager = new ScriptEngineManager();
		}

		if ( jsEngine == null ) {
			jsEngine = scriptManager.getEngineByName( "javascript" );
		}


		/**
		 * Figure out Directories.
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

			jsEngine.put( "PATH", pluginDir );
			jsEngine.eval( "var global     = {};" );
			jsEngine.eval( "var __instance = {};" );

			jsEngine.eval( "__instance.cleanup = [];" );
			jsEngine.eval( "( function () { \n" + javascript + "\n} ) ();" );
			
		} catch ( UnsupportedEncodingException e ) {
			e.printStackTrace();

		} catch ( ScriptException e ) {
			e.printStackTrace();
		}
	}


	@Override
	public void onDisable () {

		try {
			jsEngine.eval( ""
				+ "for ( var i in __instance.cleanup ) {" + System.lineSeparator()

				+ "    if ( typeof __instance.cleanup[ i ] === 'function' ) {" + System.lineSeparator()
				+ "        __instance.cleanup[ i ]()" + System.lineSeparator()
				+ "    }" + System.lineSeparator()
				+ "}"
			);

		} catch ( ScriptException e ) {
			e.printStackTrace();
		}
	}


	public void reload () {

		onDisable();
		onEnable();
	}
}

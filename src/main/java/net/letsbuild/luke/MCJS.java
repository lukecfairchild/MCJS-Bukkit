package net.letsbuild.luke;

import org.bukkit.plugin.java.JavaPlugin;
import java.nio.file.Path;

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

		try
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

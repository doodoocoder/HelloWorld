import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HelloWorld {
	static String DLL_NAME="libHelloWorld.dll";
	static {
//		System.loadLibrary("libHelloWorld");
		System.load("F:\\RcpWorkspace\\r1\\HelloWorld\\src\\lib-bin\\libHelloWorld.dll");
//		loadFromJar();
		
	}
	
	public HelloWorld() {
	}
    private static void loadFromJar() throws UnsatisfiedLinkError
    {
        File tempDir = new File(System.getProperty("user.dir"));
        File dllFile = new File(tempDir, DLL_NAME);
        // Thubby: This returns null for me!
        //InputStream in = JNative.class.getResourceAsStream("../../../lib-bin/" + DLL_NAME);
        InputStream in = HelloWorld.class.getResourceAsStream("/lib-bin/" + DLL_NAME);
        if (in == null)
        {
            if (!dllFile.exists())
            {
                throw new UnsatisfiedLinkError(DLL_NAME + " : unable to find in " + tempDir);
            }
        }
        else
        {
            if (dllFile.exists() && dllFile.canWrite())
            {
                dllFile.delete();
            }
            if (!dllFile.exists())
            {
                byte[] buffer = new byte[512];
                BufferedOutputStream out = null;
                try
                {
                    try
                    {
                        out = new BufferedOutputStream(new FileOutputStream(dllFile));
                        while (true)
                        {
                            int readed = in.read(buffer);
                            if (readed > -1)
                            {
                                out.write(buffer, 0, readed);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                    finally
                    {
                        if (out != null)
                        {
                            out.close();
                        }
                    }
                }
                catch (IOException e)
                {
                    throw new UnsatisfiedLinkError("Can't write library in " + dllFile);
                }
            }
            System.load(dllFile.toString());
        }
    }
	public native int init(int lPort);
	
	public native void print(String str);

}

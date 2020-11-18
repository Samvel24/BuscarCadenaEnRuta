
package busquedatexto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionarArchivos 
{
    private ArrayList <File> lista;
    
    public GestionarArchivos()
    {
        // constructor
        // esta lista contiene la ruta de todas las carpetas pertenecientes a un directorio raiz:
        lista = new ArrayList <>(); 
    }
    
    public void inicio()
    {
        File f = new File("C:\\Users\\samve\\Documentos\\Opera_PagGuardadas\\"); // Directorio a buscar
        arbol(f);
       
        String extension = ".txt"; // extension de archivos en los que se desea buscar
        ArrayList <String> listaDirs = new ArrayList<>();
        int cont = 0;
        System.out.println("Ruta principal de busqueda:" +f.getAbsolutePath());
        for(int i = 0; i < lista.size(); i++)
        {
            String ruta = lista.get(i).getAbsolutePath();
            if(!ruta.contains(".git"))
            {
                File[] archivosCarpeta = lista.get(i).listFiles();
                
                for(int j = 0; j < archivosCarpeta.length; j++)
                {
                    if(archivosCarpeta[j].getAbsolutePath().contains(extension))
                    {
                        //System.out.println(archivosCarpeta[j].getAbsolutePath());
                        cont++;
                        
                        // el segundo argumento es la cadena que se desea encontrar:
                        boolean flag = leerArchivo(archivosCarpeta[j], "break");
                        if(flag)
                        {
                            listaDirs.add(archivosCarpeta[j].getAbsolutePath());
                        }
                    }
                }
            }
        }
        
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        if(listaDirs.isEmpty())
        {
            System.out.println("No se encontro la cadena deseada");
        }
        else
        {
            for(String ss : listaDirs)
            {
                System.out.println("Encontrado en: " +ss);
            }
        }
        
        System.out.println("Total de archivos con extension " +extension +": " +cont);
    }
    
    /*
    Buscamos en todos los archivos de una sola carpeta que contenga unicamente
    archivos y ninguna carpeta
    */
    public void buscarEnCarpeta()
    {
        File f = new File("C:\\Users\\samve\\Documentos\\Opera_PagGuardadas\\"); // Directorio a buscar
        
        System.out.println("Ruta principal de busqueda:" +f.getAbsolutePath());
        for(File file : f.listFiles())
        {
            if(file.isFile()) // si es archivo (no carpetas)
            {
                boolean encontrado = leerArchivo(file, "break"); // (archivo, cadena a buscar)

                if(encontrado) // si se encontro la cadena
                {
                    // Imprimimos ruta de archivo en el que se encontro la cadena
                    System.out.println("Encontrado en: " +file.getAbsolutePath());
                    System.out.println("");
                }
                else
                {
                    System.out.println("No se encontro la cadena deseada");
                    System.out.println("");
                }
            }
        }
    }
    
    /*
    Nota: al leer las lineas de los archivos de la sesion de opera en la consola
    de netbeans se copian pero al pegar se pegan resultados extraños, por tanto no
    es recomedable copiarlos desde esta consola sino buscarlos en el historial de
    opera o directamente en el archivo de sesion, este programa solo sirve para 
    orientarme y no buscar linea por linea en todo el archivo de sesion que puede 
    ser muy complicado de leer.
    */
    public void buscarEnArchivoIndividual()
    {
        String directorio = "C:\\Users\\samve\\AppData\\Roaming\\Opera Software\\Opera Stable\\Sessions\\";
        String archivo = "Session_13249154130313986";
        File f = new File(directorio +archivo); // Directorio a buscar
        
        List[] lista = leerArchivo2(f, "https"); // (archivo, cadena a buscar)
        
        ArrayList <Integer> numLineas = (ArrayList<Integer>)lista[0];
        ArrayList <String> lineas = (ArrayList<String>)lista[1];
        
        if(!lineas.isEmpty()) // si el arraylist no esta vacio
        {
            System.out.println("Num de lineas encontradas con la cadena deseada: " +numLineas.size());
            for(int i = 0; i < numLineas.size(); i++)
            {
                System.out.println("[Linea (" +numLineas.get(i) +"): " +lineas.get(i) +"]");
            }
        }
        else
        {
            System.out.println("No se encontro la cadena deseada");
        }
    }
    
    // funcion que se llama desde buscarEnArchivoIndividual()
    private List[] leerArchivo2(File archivo, String busqueda)
    {
        System.out.println("Leyendo archivo " +archivo.getAbsolutePath());
        
        String cadena;
        ArrayList <String> lineasArchivo = new ArrayList<>(); // lineas del archivo (Strings)
        ArrayList <String> lineasEncontradas = new ArrayList<>(); // numeros de lineas en que se encontro cadena
        ArrayList <Integer> numLineas = new ArrayList<>();
        try 
        {
            FileReader lector = new FileReader(archivo);
            BufferedReader lectura = new BufferedReader(lector);          
          
            cadena = lectura.readLine(); 
            
            while(cadena != null)
            {
                cadena = lectura.readLine();
                
                if(cadena != null)
                {
                    lineasArchivo.add(cadena);
                }
            }   
        } 
        catch (FileNotFoundException ex) 
        {
           System.err.println("Error: " +ex);    
        }
        catch (IOException ex) 
        {
            System.err.println("Error: " +ex);    
        }
        
        System.out.println("Total de lineas en el archivo: " +lineasArchivo.size());
        for(int i = 0; i < lineasArchivo.size(); i++)
        {   
            if(lineasArchivo.get(i).contains(busqueda))
            {
                numLineas.add(i);
                lineasEncontradas.add(lineasArchivo.get(i));
            }
        }
        
        return new List[]{numLineas, lineasEncontradas};
    }
    
    private boolean leerArchivo(File archivo, String busqueda)
    {
        //File archivo = new File("C:\\www\\ito-jobs.localhost\\arbol.doc");
        System.out.println("Leyendo archivo " +archivo.getAbsolutePath());
        
        String cadena;
        ArrayList <String> lineas = new ArrayList <>();
        try 
        {
            FileReader lector = new FileReader(archivo);
            BufferedReader lectura = new BufferedReader(lector);          
          
            cadena = lectura.readLine(); 
            
            while(cadena != null)
            {
                cadena = lectura.readLine();
                
                if(cadena != null)
                {
                    lineas.add(cadena);
                }
            }   
        } 
        catch (FileNotFoundException ex) 
        {
           System.err.println("Error: " +ex);    
        }
        catch (IOException ex) 
        {
            System.err.println("Error: " +ex);    
        }
        
        boolean bandera = false;
        for(String l : lineas)
        {   
            if(l.contains(busqueda))
            {
                bandera = true;
            }
        }
        
        return bandera;
    }
    
    private void arbol(File file)
    {
        //System.out.println("Arbol: " +file.getAbsolutePath());
        for(File f : file.listFiles())
        {   
            if(f.isDirectory())
            {
                lista.add(new File(f.getAbsolutePath()));
                
                arbol(f); // usamos recursividad
            }
        }
    }
    
    private String[] getFiles(String dir_path) 
    {
        String[] arr_res = null;
        
        File f = new File( dir_path );

        if (f.isDirectory()) 
        {
            List<String> res = new ArrayList<>();
            File[] arr_content = f.listFiles();

            int size = arr_content.length; //System.out.println("Size: " +size);
            
            for(int i = 0; i < size; i++)
            {

                if (arr_content[i].isFile())
                {
                    if(arr_content[i].toString().contains(".php"))
                        res.add(arr_content[i].toString());
                }
            }

            arr_res = res.toArray(new String[0]);
        } 
        else
            System.err.println("¡ Path NO válido !");

        return arr_res;
    }
}


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

        lista = new ArrayList <>();
    }
    
    public void inicio()
    {
        File f = new File("C:\\www\\ito-jobs.localhost\\"); // Directorio a buscar
        arbol(f);
        
        String extension = ".php"; // extension de archivos en los que se desea buscar
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
                        boolean flag = leerArchivo(archivosCarpeta[j], "guest");
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

import java.util.*;
import java.util.Scanner;
import org.apache.commons.math3.util.Precision;

// Enum para saber si se aplica impuesto en el producto o no
enum tipo{
	BASICO, IMPONIBLE
}

//Clase que almacena información del producto
class Producto{
	String nombre;
	tipo impuesto;
	double precio;
	boolean importado;

	private void setNombre(String nombre){
		this.nombre = nombre;
	}

	private void setImpuesto(tipo impuesto){
		this.impuesto = impuesto;
	}

	private void setPrecio(double precio){
		this.precio = precio;
	}

	private void setImportado(boolean importado){
		this.importado = importado;
	}

	

	public Producto(String nom, tipo imp, double prec, boolean importado){
		this.setNombre(nom);
		this.setImpuesto(imp);
		this.setPrecio(prec);
		this.setImportado(importado);
	}

	public String getNombre(){
		return this.nombre;
	}

	public double getprecio(){
		return this.precio;
	}

	public tipo getTipo(){
		return this.impuesto;
	}

	public boolean getImportado(){
		return this.importado;
	}

}

//Clase en la que se almacenan todos los productos que están a la venta
class ListaProductos {

	List<Producto> productos = new ArrayList<Producto>();

	void inicializar() {
		
		productos.add(new Producto("CD de música", tipo.IMPONIBLE, 14.99, false));
		productos.add(new Producto("Libro", tipo.BASICO, 12.49, false));
		productos.add(new Producto("Barrita de chocolate", tipo.BASICO, 0.85, false));
		productos.add(new Producto("Caja de bombones importados", tipo.BASICO, 10.00, true));
		productos.add(new Producto("Frasco de perfume importado", tipo.IMPONIBLE, 47.50, true));
		productos.add(new Producto("Frasco de perfume importado", tipo.IMPONIBLE, 27.99, true));
		productos.add(new Producto("Frasco de perfume", tipo.IMPONIBLE, 47.50, false));
		productos.add(new Producto("Caja de pastillas para el dolor de cabeza", tipo.BASICO, 9.75, false));
		productos.add(new Producto("Caja de bombones importadi", tipo.BASICO, 9.75, false));
	}

	public ListaProductos(){
		this.inicializar();
	}

	public void AniadeProducto(Producto p){
		productos.add(p);
	}

	//Muestra los productos disponibles
	public void muestraDisponibles(){
		List<Producto> conjunto = this.getProductos();

		System.out.println("Lista de elementos con sus respectivos códigos");
		for (int i = 0; i < conjunto.size(); i++){
			System.out.println(conjunto.get(i).getNombre() + ", cuyo precio es " + " " + conjunto.get(i).getprecio() + "€ y su código: " + i);
		}
	}

	//devuelve una lista con todos los productos
	public List<Producto> getProductos(){
		return this.productos;
	} 

	//Devuelve un producto a partir de su índice
	public Producto getProducto(int i){
		return this.productos.get(i);
	}


}

//Clase que almacena la lista de la compra, y calcula el precio de esta
class Ticket{
	List<Producto> compra = new ArrayList<Producto>();
	double total = 0.0;
	double impuesto = 0.0;

	public Ticket(){

	}

	public void aniadeProducto (Producto p){
		compra.add(p);
	}

	//calcula el precio total y los impuestos (de cada producto y totales)
	public void calculaPrecio(){
		System.out.println("RESULTADO:");
		for(int i=0; i < compra.size(); i++){
			double impuestoProducto = 0.0;
			double totalProducto = 0.0;

			Producto actual = compra.get(i);
			totalProducto += actual.getprecio();
			if (compra.get(i).getTipo().equals(tipo.IMPONIBLE)){
				double imp = actual.getprecio() * 0.1;
				impuestoProducto += ((double)Math.round(imp *100))/100;
			}
			if (compra.get(i).getImportado()){
				double imp = actual.getprecio() * 0.05;
				impuestoProducto += ((double)Math.round(imp *100))/100;
			}

			totalProducto += impuestoProducto;
			System.out.println("1 " + actual.getNombre() + ": " + totalProducto + " €");
			total += totalProducto;
			impuesto += impuestoProducto;
		}

		System.out.println("impuesto sobre las ventas: " + impuesto + " €");
		System.out.println("Total: " + total + " €");

	}
}

//Clase principal que sirve como interfaz de texto y controlador.
public class Cajero{


	public static void main(String[] args) {	
		ListaProductos lista = new ListaProductos();
		Ticket compra = new Ticket();
		String opcion;

		do{
			Scanner teclado = new Scanner(System.in);
			System.out.println("Qué operación desea realizar:\nSalir(s)\nAñadir Nuevo producto(a)\nRealizar Compra(c)" );
			opcion = teclado.nextLine();

			//Si se quiere añadir un elemento
			if (opcion.equals("a")){
				String otra;
				String nombre;
				tipo impuesto;
				double precio;
				boolean importado;

				for (int i=0; i< lista.getProductos().size(); i++){
					System.out.println(lista.getProductos().get(i).getNombre());
				}


				//Pedimos por pantalla las distintas variables que componen un objeto
				System.out.println("Nombre del producto");
				nombre = teclado.nextLine();
				System.out.println("¿Es un producto básico (Comida, medicacion,...)?(s/n)");
				if (teclado.nextLine().equals("s")){
					impuesto = tipo.BASICO;
				}else{
					impuesto = tipo.IMPONIBLE;
				}
				System.out.println("indique el precio del producto");
				precio = Double.parseDouble(teclado.nextLine());
				System.out.println("¿es un producto importado?(s/n):");
				if(teclado.nextLine().equals("s")){
					importado = true;
				}else {
					importado = false;
				}

				lista.AniadeProducto(new Producto(nombre, impuesto, precio, importado));




				//Para realizar otra operación antes de salir de la ejecución
				System.out.println("¿Deseas relaizar otra operación?");
				otra = teclado.nextLine();
				if (otra.equals("n")){
					opcion = "s";
				}
			
				//opción para registrar elementos comprados
			}else if (opcion.equals("c")){
				String otra;
				String codigo;

				lista.muestraDisponibles();

				System.out.println("Por favor introduzca el codigo de los elementos que ha comprado, use s para salir:");
				do{
					codigo = teclado.nextLine();
					if(!codigo.equals("s")){
						compra.aniadeProducto(lista.getProducto(Integer.parseInt(codigo)));
					}
				}while(!codigo.equals("s"));

				compra.calculaPrecio();

				System.out.println("¿Deseas relaizar otra operación?");
				otra = teclado.nextLine();

				if (otra.equals("n")){
					opcion = "s";
				}

			}

		} while(!opcion.equals("s"));
		
		
		
	}
}
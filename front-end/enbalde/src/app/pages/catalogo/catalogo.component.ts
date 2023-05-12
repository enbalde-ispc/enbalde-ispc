import { Component, Input } from '@angular/core';
import { Producto } from '../producto/modelo/modelo.producto';
import { ProductosService } from 'src/app/productos.service';

@Component({
  selector: 'app-catalogo',
  templateUrl: './catalogo.component.html',
  styleUrls: ['./catalogo.component.css'],
  providers: [ ProductosService ] // declaramos un proveedor de servicio,
                                  // ProductosService
})

export class CatalogComponent {
  carrito: Producto[] = [];
  @Input() productos: Producto [] = [];

  constructor(public productosService: ProductosService) {
  }

  ngOnInit() : void {
    this.productos = this.productosService.obtenerProductos();
  }

  //Acomodé los ID y agg las img, junto con la funcion de agregarAlCarrito();
  agregarAlCarrito(producto: Producto) {
    if (producto.cantidadDisponible > 0) {
      producto.cantidadDisponible--;
      this.carrito.push(producto);
      alert('Agregaste al carrito un helado de: ' + producto.titulo)
    }
    if(producto.cantidadDisponible === 0){
      alert('No hay mas helado disponible de: '+ producto.titulo)
    }
  }
}
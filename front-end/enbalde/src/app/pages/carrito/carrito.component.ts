import { Component, Input } from '@angular/core';
import { Envio } from '../../models/modelo.envio';
import { Producto } from '../../models/modelo.producto';
import { Seleccion } from '../../models/modelo.seleccion';
import { Router } from '@angular/router';
import { EnviosService } from 'src/app/services/envios.service';
import { CarritoService } from 'src/app/services/carrito.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-carrito',
  templateUrl: './carrito.component.html',
  styleUrls: ['./carrito.component.css'],
  providers: [CarritoService, EnviosService]
})

export class CarritoComponent  {
  totalCarrito: number = 0;
  envioElegido: Envio;

  @Input() carrito: Seleccion[];
  @Input() envios: Envio[];

  uncheckOther(event: Event) {
    const checkbox = event.target as HTMLInputElement;
    const checkboxes = Array.from(document.getElementsByName('opcionPago')) as HTMLInputElement[];
    checkboxes.forEach(cb => {
      if (cb !== checkbox) {
        cb.checked = false;
      }
    });
  }

  constructor(private carritoService : CarritoService, private enviosService : EnviosService, private router: Router, private authService: AuthService) {
    this.envioElegido = {
      id: -1,
      nombre: "Default",
      monto: 0
    }

    this.carrito = [];
    this.envios = [];
  }

  // Agrego enrutamiento
  aggProductos() {
    this.router.navigate(['/catalogo']);
  }

  ngOnInit(): void {
    this.enviosService.obtenerEnvios()
      .subscribe((envios: Envio[]) => {
        this.envios = envios;
        this.envioElegido = envios[0];
      });

    this.carritoService.obtenerProductosCarrito()
      .subscribe((selecciones: Seleccion[]) => {
        this.carrito = selecciones;
        this.carritoSuma();
      });
  }

  seleccionarEnvio(event: any) {
    this.envioElegido = this.envios.filter(p => p.id == event.target.value)[0];
    this.carritoSuma()
  }

  carritoSuma(): void {
    this.totalCarrito = this.carrito.reduce(function(t, i) { return t + i.total; }, 0.00) + this.envioElegido.monto;
  }

  // Elimino todos los productos una vez pagados y restauro el valor total
  pagar(){
    alert('Has pagado correctamente');
    this.carritoService.checkout(this.envioElegido)
      .subscribe(v => {
        this.carritoService.refrescarCarrito()
          .subscribe(c => {
            if (c > 0) this.authService.cambiarCarrito(c);

            this.totalCarrito = 0; // Cree esta variable solamente para poder hacer uso del totalCarrito
            this.carrito = [];
            const carritoReducido = this.getCarritoReducido();
          });
      })
  }

  // Agrego un producto al carrito
  agregarAlCarrito(producto: Producto) {
    if (producto.cantidad > 0) {
      producto.cantidad--;
      this.carrito.push({ "articulo": producto, "cantidad": 1, "ofertas": [], "descuento": 0, "total": producto.precio });
    }
    if(producto.cantidad === 0){
      alert('No hay mas helado disponible de: '+ producto.nombre)
    }

    this.carritoSuma();
  }

  // Elimino un producto al carrito
  eliminarDelCarrito(producto: Producto) {
    const index = this.carrito.findIndex(p => p.articulo.id === producto.id);
    if (index !== -1) {
      this.carrito.splice(index, 1);
      producto.cantidad++;
      this.carritoSuma();
    }
  }

 // Creo un array para almacenar los elementos repetidos
  getCarritoReducido(){
    const carritoReducido: any[] = [];
    this.carrito.forEach((seleccion) => {
      const index = carritoReducido.findIndex((item) => item.articulo.id === seleccion.articulo.id);
      if (index !== -1) {
        carritoReducido[index].cantidad++;
      } else {
        carritoReducido.push({ "articulo": seleccion.articulo, "cantidad": 1 });
      }
    });

    return carritoReducido;
  }
}

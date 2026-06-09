/*
 Responsable: Franco
 Tema: Arbol AVL

 Este archivo le corresponde a Franco.

 Implementacion esperada:
 - arbol AVL generico con clave comparable

 Uso en este proyecto:
 - backlog por fecha o clave comparable

 Clase interna esperada:
 - NodoAVL
   - Comparable clave
   - Object valor
   - NodoAVL izquierdo
   - NodoAVL derecho
   - int altura

 Metodos publicos esperados:
 - void insertar(Comparable clave, Object valor)
 - Object buscar(Comparable clave)
 - void eliminar(Comparable clave)
 - List<Object> buscarPorRango(Comparable desde, Comparable hasta)
 - void imprimirEnOrden()

 Metodos privados esperados:
 - int altura(NodoAVL nodo)
 - int factorBalance(NodoAVL nodo)
 - NodoAVL rotarDerecha(NodoAVL y)
 - NodoAVL rotarIzquierda(NodoAVL x)
 - NodoAVL rotarIzquierdaDerecha(NodoAVL nodo)
 - NodoAVL rotarDerechaIzquierda(NodoAVL nodo)
 - NodoAVL rebalancear(NodoAVL nodo)

 Regla importante:
 - incluir las 4 rotaciones pedidas
*/

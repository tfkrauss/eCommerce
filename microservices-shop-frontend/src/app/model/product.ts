export interface Product {
  id?: string;
  skuCode: string;
  name: string;
  description: string;
  price: number;
}

//Quantity and skuCode go to inventory service. skuCode and everything besides quantity go to product service.
export interface ProductWithInventoryRequest {
  product: Product;
  quantity: number;
}

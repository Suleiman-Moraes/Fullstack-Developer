import { BaseResourceModel } from 'src/app/shared/models/base-resource.model';
import { Cliente } from './cliente.model';
import { DetalhePedido } from './detalhe-pedido.model';

export class Pedido extends BaseResourceModel {
    constructor(
        public itensQuantidadeTotal?: number,
        public frete?: number,
        public itensValorTotal?: number,
        public total?: number,
        public data?: Date,
        public usuarioId?: number,
        public cliente?: Cliente,
        public detalhePedidos?: DetalhePedido[]
    ) {
        super();
    }

    static fromJson(jsonData: any): Pedido {
        return Object.assign(new Pedido(), jsonData);
    }
}
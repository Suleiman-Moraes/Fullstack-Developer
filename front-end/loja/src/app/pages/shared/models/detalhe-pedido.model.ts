import { BaseResourceModel } from 'src/app/shared/models/base-resource.model';
import { Produto } from './produto.model';

export class DetalhePedido extends BaseResourceModel {
    constructor(
        public quantidade?: number,
        public total?: number,
        public valorEpoca?: number,
        public produto?: Produto
    ) {
        super();
    }

    static fromJson(jsonData: any): DetalhePedido {
        return Object.assign(new DetalhePedido(), jsonData);
    }
}
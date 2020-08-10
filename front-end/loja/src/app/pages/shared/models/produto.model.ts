import { BaseResourceModel } from 'src/app/shared/models/base-resource.model';

export class Produto extends BaseResourceModel {
    constructor(
        public precoUnitario?: number,
        public imagemUrl?: string,
        public nome?: string,
        public codigo?: string
    ) {
        super();
    }

    static fromJson(jsonData: any): Produto {
        return Object.assign(new Produto(), jsonData);
    }
}
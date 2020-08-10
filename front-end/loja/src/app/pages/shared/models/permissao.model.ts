import { BaseResourceModel } from 'src/app/shared/models/base-resource.model';

export class Permissao extends BaseResourceModel {
    constructor(
        public nome?: string
    ) {
        super();
    }

    static fromJson(jsonData: any): Permissao {
        return Object.assign(new Permissao(), jsonData);
    }
}
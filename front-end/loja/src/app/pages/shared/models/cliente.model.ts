import { BaseResourceModel } from 'src/app/shared/models/base-resource.model';

export class Cliente extends BaseResourceModel {
    constructor(
        public nome?: string,
        public codigo?: string
    ) {
        super();
    }

    static fromJson(jsonData: any): Cliente {
        return Object.assign(new Cliente(), jsonData);
    }
}
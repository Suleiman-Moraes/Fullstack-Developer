import { BaseResourceModel } from 'src/app/shared/models/base-resource.model';
import { Permissao } from './permissao.model';

export class Usuario extends BaseResourceModel {
    constructor(
        public login?: string,
        public senha?: string,
        public nome?: string,
        public dataInclusao?: Date,
        public ativo?: boolean,
        public clienteId?: number,
        public permissoes?: Permissao[],
    ) {
        super();
    }

    static fromJson(jsonData: any): Usuario {
        return Object.assign(new Usuario(), jsonData);
    }
}
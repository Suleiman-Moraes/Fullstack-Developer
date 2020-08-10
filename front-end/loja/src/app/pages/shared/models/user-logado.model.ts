import { Usuario } from './usuario.model';

export class UserLogado {
    constructor(
        public token?: string,
        public user?: Usuario,
        public roles?: string[]
    ) { }
}
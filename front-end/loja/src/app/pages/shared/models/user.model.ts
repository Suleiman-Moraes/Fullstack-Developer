export class User {
    constructor(
        public login?: string,
        public password?: string,
    ) { }

    static fromJson(jsonData: any): User {
        return Object.assign(new User(), jsonData);
    }
}
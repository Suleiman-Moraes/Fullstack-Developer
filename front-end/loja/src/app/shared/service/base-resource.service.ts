import { ResponseApi } from './../models/response-api.model';
import { BaseResourceModel } from 'src/app/shared/models/base-resource.model';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Injector } from '@angular/core';

export abstract class BaseResourceService<T extends BaseResourceModel> {

    protected http: HttpClient;
    protected responseApi: ResponseApi;

    constructor(
        protected apiPath: string,
        protected injector: Injector,
        protected jsonDataToResourceFn: (jsonData: any) => T
    ) {
        this.http = injector.get(HttpClient);
    }

    getAll(): Observable<ResponseApi> {
        return this.http.get(this.apiPath).pipe(
            map(this.fromJsonResponseApi.bind(this)),
            catchError(this.handleError)
        )
    }

    getById(id: number): Observable<ResponseApi> {
        return this.getUtil(`${this.apiPath}/${id}`);
    }

    imprimirUtil(url: string): Observable<ResponseApi> {
        return this.getUtil(url);
    }

    getUtil(url: string): Observable<ResponseApi> {
        return this.http.get(url).pipe(
            map(this.fromJsonResponseApi.bind(this)),
            catchError(this.handleError)
        )
    }

    imprimirUtilComBody(url: string, object): Observable<ResponseApi> {
        return this.http.post(url, object).pipe(
            map(this.fromJsonResponseApi.bind(this)),
            catchError(this.handleError)
        )
    }

    create(resource: T): Observable<ResponseApi> {
        return this.http.post(this.apiPath, resource).pipe(
            map(this.fromJsonResponseApi.bind(this)),
            catchError(this.handleError)
        )
    }

    update(resource: T): Observable<ResponseApi> {
        return this.http.put(this.apiPath, resource).pipe(
            map(this.fromJsonResponseApi.bind(this)),
            catchError(this.handleError)
        );
    }

    delete(id: number): Observable<ResponseApi> {
        return this.http.delete(`${this.apiPath}/${id}`).pipe(
            map(this.fromJsonResponseApi.bind(this)),
            catchError(this.handleError)
        );
    }

    findByField(field: string, value: any): Observable<ResponseApi> {
        return this.http.get(`${this.apiPath}/findbyfield/?field=${field}&value=${value}`).pipe(
            map(this.fromJsonResponseApi.bind(this)),
            catchError(this.handleError)
        );
    }

    enviarFormulario(resource: T, metodo: boolean): Observable<ResponseApi> {
        return metodo ? this.update(resource) : this.create(resource);
    }

    findbyparamssingle(page: number, count: number): Observable<ResponseApi> {
        return this.http.get
            (`${this.apiPath}/findbyparamssingle?page=${page}&count=${count}`)
            .pipe(
                map(this.fromJsonResponseApi.bind(this)),
                catchError(this.handleError)
            );
    }

    // PROTECTED METHODS
    protected jsonDataToResources(jsonData: any[]): T[] {
        const resources: T[] = [];
        jsonData.forEach(
            element => resources.push(this.jsonDataToResourceFn(element))
        );
        return resources;
    }

    protected jsonDataToResource(jsonData: any): T {
        return this.jsonDataToResourceFn(jsonData);
    }

    protected handleError(error: any): Observable<any> {
        console.log("ERRO NA REQUISIÇÃO => ", error);
        return throwError(error);
    }

    protected getResponseApi(json: any): ResponseApi {
        // this.getResponseApi(json)
        json.subscribe((responseApi: ResponseApi) => {
            this.responseApi = responseApi;
        }, err => {
            this.handleError(err);
        });
        return this.responseApi;
    }

    protected jsonDataBoolean(json: any): boolean {
        return Object.assign(new ResponseApi(), json);
    }

    protected fromJsonResponseApi(jsonData: any): ResponseApi {
        return Object.assign(new ResponseApi(), jsonData);
    }
}
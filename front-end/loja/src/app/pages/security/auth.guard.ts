import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../shared/services/authentication.service';
import { UserLogado } from '../shared/models/user-logado.model';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) { }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        const currentUser: UserLogado = this.authenticationService.currentUserValue;
        if (currentUser) {
            if (route.data.roles) {
                for (let i = 0; i < currentUser.roles.length; i++) {
                    if (route.data.roles.indexOf(currentUser.roles[i] + '') >= 0) {
                        return this.verificarPermissao(currentUser, route, state);
                    }
                }
                this.router.navigate(['404']);
                return false;
            }
            return this.verificarPermissao(currentUser, route, state);
        }

        // not logged in so redirect to login page with the return url
        this.router.navigate(['/login']);
        return false;
    }

    private verificarPermissao(currentUser: UserLogado, route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        return true;
    }
}
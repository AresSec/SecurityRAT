import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOptColumnType, OptColumnType } from 'app/shared/model/opt-column-type.model';
import { OptColumnTypeService } from './opt-column-type.service';
import { OptColumnTypeComponent } from './opt-column-type.component';
import { OptColumnTypeDetailComponent } from './opt-column-type-detail.component';
import { OptColumnTypeUpdateComponent } from './opt-column-type-update.component';

@Injectable({ providedIn: 'root' })
export class OptColumnTypeResolve implements Resolve<IOptColumnType> {
  constructor(private service: OptColumnTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOptColumnType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((optColumnType: HttpResponse<OptColumnType>) => {
          if (optColumnType.body) {
            return of(optColumnType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OptColumnType());
  }
}

export const optColumnTypeRoute: Routes = [
  {
    path: '',
    component: OptColumnTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumnType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OptColumnTypeDetailComponent,
    resolve: {
      optColumnType: OptColumnTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumnType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OptColumnTypeUpdateComponent,
    resolve: {
      optColumnType: OptColumnTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumnType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OptColumnTypeUpdateComponent,
    resolve: {
      optColumnType: OptColumnTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumnType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

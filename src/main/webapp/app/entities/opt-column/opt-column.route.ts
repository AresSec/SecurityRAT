import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOptColumn, OptColumn } from 'app/shared/model/opt-column.model';
import { OptColumnService } from './opt-column.service';
import { OptColumnComponent } from './opt-column.component';
import { OptColumnDetailComponent } from './opt-column-detail.component';
import { OptColumnUpdateComponent } from './opt-column-update.component';

@Injectable({ providedIn: 'root' })
export class OptColumnResolve implements Resolve<IOptColumn> {
  constructor(private service: OptColumnService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOptColumn> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((optColumn: HttpResponse<OptColumn>) => {
          if (optColumn.body) {
            return of(optColumn.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OptColumn());
  }
}

export const optColumnRoute: Routes = [
  {
    path: '',
    component: OptColumnComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OptColumnDetailComponent,
    resolve: {
      optColumn: OptColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OptColumnUpdateComponent,
    resolve: {
      optColumn: OptColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OptColumnUpdateComponent,
    resolve: {
      optColumn: OptColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

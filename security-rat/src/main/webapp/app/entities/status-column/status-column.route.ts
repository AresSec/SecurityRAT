import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStatusColumn, StatusColumn } from 'app/shared/model/status-column.model';
import { StatusColumnService } from './status-column.service';
import { StatusColumnComponent } from './status-column.component';
import { StatusColumnDetailComponent } from './status-column-detail.component';
import { StatusColumnUpdateComponent } from './status-column-update.component';

@Injectable({ providedIn: 'root' })
export class StatusColumnResolve implements Resolve<IStatusColumn> {
  constructor(private service: StatusColumnService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusColumn> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((statusColumn: HttpResponse<StatusColumn>) => {
          if (statusColumn.body) {
            return of(statusColumn.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StatusColumn());
  }
}

export const statusColumnRoute: Routes = [
  {
    path: '',
    component: StatusColumnComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.statusColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StatusColumnDetailComponent,
    resolve: {
      statusColumn: StatusColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.statusColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StatusColumnUpdateComponent,
    resolve: {
      statusColumn: StatusColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.statusColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StatusColumnUpdateComponent,
    resolve: {
      statusColumn: StatusColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.statusColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

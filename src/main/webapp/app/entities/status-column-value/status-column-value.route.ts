import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStatusColumnValue, StatusColumnValue } from 'app/shared/model/status-column-value.model';
import { StatusColumnValueService } from './status-column-value.service';
import { StatusColumnValueComponent } from './status-column-value.component';
import { StatusColumnValueDetailComponent } from './status-column-value-detail.component';
import { StatusColumnValueUpdateComponent } from './status-column-value-update.component';

@Injectable({ providedIn: 'root' })
export class StatusColumnValueResolve implements Resolve<IStatusColumnValue> {
  constructor(private service: StatusColumnValueService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatusColumnValue> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((statusColumnValue: HttpResponse<StatusColumnValue>) => {
          if (statusColumnValue.body) {
            return of(statusColumnValue.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StatusColumnValue());
  }
}

export const statusColumnValueRoute: Routes = [
  {
    path: '',
    component: StatusColumnValueComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.statusColumnValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StatusColumnValueDetailComponent,
    resolve: {
      statusColumnValue: StatusColumnValueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.statusColumnValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StatusColumnValueUpdateComponent,
    resolve: {
      statusColumnValue: StatusColumnValueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.statusColumnValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StatusColumnValueUpdateComponent,
    resolve: {
      statusColumnValue: StatusColumnValueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.statusColumnValue.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

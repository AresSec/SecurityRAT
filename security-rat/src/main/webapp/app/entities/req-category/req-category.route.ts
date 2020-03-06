import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReqCategory, ReqCategory } from 'app/shared/model/req-category.model';
import { ReqCategoryService } from './req-category.service';
import { ReqCategoryComponent } from './req-category.component';
import { ReqCategoryDetailComponent } from './req-category-detail.component';
import { ReqCategoryUpdateComponent } from './req-category-update.component';

@Injectable({ providedIn: 'root' })
export class ReqCategoryResolve implements Resolve<IReqCategory> {
  constructor(private service: ReqCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReqCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reqCategory: HttpResponse<ReqCategory>) => {
          if (reqCategory.body) {
            return of(reqCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReqCategory());
  }
}

export const reqCategoryRoute: Routes = [
  {
    path: '',
    component: ReqCategoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.reqCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReqCategoryDetailComponent,
    resolve: {
      reqCategory: ReqCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.reqCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReqCategoryUpdateComponent,
    resolve: {
      reqCategory: ReqCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.reqCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReqCategoryUpdateComponent,
    resolve: {
      reqCategory: ReqCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.reqCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

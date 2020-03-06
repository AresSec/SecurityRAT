import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOptColumnContent, OptColumnContent } from 'app/shared/model/opt-column-content.model';
import { OptColumnContentService } from './opt-column-content.service';
import { OptColumnContentComponent } from './opt-column-content.component';
import { OptColumnContentDetailComponent } from './opt-column-content-detail.component';
import { OptColumnContentUpdateComponent } from './opt-column-content-update.component';

@Injectable({ providedIn: 'root' })
export class OptColumnContentResolve implements Resolve<IOptColumnContent> {
  constructor(private service: OptColumnContentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOptColumnContent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((optColumnContent: HttpResponse<OptColumnContent>) => {
          if (optColumnContent.body) {
            return of(optColumnContent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OptColumnContent());
  }
}

export const optColumnContentRoute: Routes = [
  {
    path: '',
    component: OptColumnContentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumnContent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OptColumnContentDetailComponent,
    resolve: {
      optColumnContent: OptColumnContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumnContent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OptColumnContentUpdateComponent,
    resolve: {
      optColumnContent: OptColumnContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumnContent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OptColumnContentUpdateComponent,
    resolve: {
      optColumnContent: OptColumnContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.optColumnContent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

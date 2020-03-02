import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlternativeSet, AlternativeSet } from 'app/shared/model/alternative-set.model';
import { AlternativeSetService } from './alternative-set.service';
import { AlternativeSetComponent } from './alternative-set.component';
import { AlternativeSetDetailComponent } from './alternative-set-detail.component';
import { AlternativeSetUpdateComponent } from './alternative-set-update.component';

@Injectable({ providedIn: 'root' })
export class AlternativeSetResolve implements Resolve<IAlternativeSet> {
  constructor(private service: AlternativeSetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlternativeSet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alternativeSet: HttpResponse<AlternativeSet>) => {
          if (alternativeSet.body) {
            return of(alternativeSet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AlternativeSet());
  }
}

export const alternativeSetRoute: Routes = [
  {
    path: '',
    component: AlternativeSetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.alternativeSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlternativeSetDetailComponent,
    resolve: {
      alternativeSet: AlternativeSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.alternativeSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlternativeSetUpdateComponent,
    resolve: {
      alternativeSet: AlternativeSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.alternativeSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlternativeSetUpdateComponent,
    resolve: {
      alternativeSet: AlternativeSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.alternativeSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

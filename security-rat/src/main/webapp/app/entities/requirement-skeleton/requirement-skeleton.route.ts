import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRequirementSkeleton, RequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';
import { RequirementSkeletonService } from './requirement-skeleton.service';
import { RequirementSkeletonComponent } from './requirement-skeleton.component';
import { RequirementSkeletonDetailComponent } from './requirement-skeleton-detail.component';
import { RequirementSkeletonUpdateComponent } from './requirement-skeleton-update.component';

@Injectable({ providedIn: 'root' })
export class RequirementSkeletonResolve implements Resolve<IRequirementSkeleton> {
  constructor(private service: RequirementSkeletonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRequirementSkeleton> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((requirementSkeleton: HttpResponse<RequirementSkeleton>) => {
          if (requirementSkeleton.body) {
            return of(requirementSkeleton.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RequirementSkeleton());
  }
}

export const requirementSkeletonRoute: Routes = [
  {
    path: '',
    component: RequirementSkeletonComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.requirementSkeleton.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RequirementSkeletonDetailComponent,
    resolve: {
      requirementSkeleton: RequirementSkeletonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.requirementSkeleton.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RequirementSkeletonUpdateComponent,
    resolve: {
      requirementSkeleton: RequirementSkeletonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.requirementSkeleton.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RequirementSkeletonUpdateComponent,
    resolve: {
      requirementSkeleton: RequirementSkeletonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.requirementSkeleton.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

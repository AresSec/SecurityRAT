import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISlideTemplate, SlideTemplate } from 'app/shared/model/slide-template.model';
import { SlideTemplateService } from './slide-template.service';
import { SlideTemplateComponent } from './slide-template.component';
import { SlideTemplateDetailComponent } from './slide-template-detail.component';
import { SlideTemplateUpdateComponent } from './slide-template-update.component';

@Injectable({ providedIn: 'root' })
export class SlideTemplateResolve implements Resolve<ISlideTemplate> {
  constructor(private service: SlideTemplateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISlideTemplate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((slideTemplate: HttpResponse<SlideTemplate>) => {
          if (slideTemplate.body) {
            return of(slideTemplate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SlideTemplate());
  }
}

export const slideTemplateRoute: Routes = [
  {
    path: '',
    component: SlideTemplateComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.slideTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SlideTemplateDetailComponent,
    resolve: {
      slideTemplate: SlideTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.slideTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SlideTemplateUpdateComponent,
    resolve: {
      slideTemplate: SlideTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.slideTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SlideTemplateUpdateComponent,
    resolve: {
      slideTemplate: SlideTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.slideTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

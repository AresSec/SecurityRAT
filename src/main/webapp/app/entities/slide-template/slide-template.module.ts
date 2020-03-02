import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { SlideTemplateComponent } from './slide-template.component';
import { SlideTemplateDetailComponent } from './slide-template-detail.component';
import { SlideTemplateUpdateComponent } from './slide-template-update.component';
import { SlideTemplateDeleteDialogComponent } from './slide-template-delete-dialog.component';
import { slideTemplateRoute } from './slide-template.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(slideTemplateRoute)],
  declarations: [SlideTemplateComponent, SlideTemplateDetailComponent, SlideTemplateUpdateComponent, SlideTemplateDeleteDialogComponent],
  entryComponents: [SlideTemplateDeleteDialogComponent]
})
export class SecurityRatSlideTemplateModule {}

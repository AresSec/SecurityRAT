import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityRatSharedModule } from 'app/shared/shared.module';
import { ReqCategoryComponent } from './req-category.component';
import { ReqCategoryDetailComponent } from './req-category-detail.component';
import { ReqCategoryUpdateComponent } from './req-category-update.component';
import { ReqCategoryDeleteDialogComponent } from './req-category-delete-dialog.component';
import { reqCategoryRoute } from './req-category.route';

@NgModule({
  imports: [SecurityRatSharedModule, RouterModule.forChild(reqCategoryRoute)],
  declarations: [ReqCategoryComponent, ReqCategoryDetailComponent, ReqCategoryUpdateComponent, ReqCategoryDeleteDialogComponent],
  entryComponents: [ReqCategoryDeleteDialogComponent]
})
export class SecurityRatReqCategoryModule {}

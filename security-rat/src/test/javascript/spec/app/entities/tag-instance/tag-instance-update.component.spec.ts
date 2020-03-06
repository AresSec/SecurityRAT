import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SecurityRatTestModule } from '../../../test.module';
import { TagInstanceUpdateComponent } from 'app/entities/tag-instance/tag-instance-update.component';
import { TagInstanceService } from 'app/entities/tag-instance/tag-instance.service';
import { TagInstance } from 'app/shared/model/tag-instance.model';

describe('Component Tests', () => {
  describe('TagInstance Management Update Component', () => {
    let comp: TagInstanceUpdateComponent;
    let fixture: ComponentFixture<TagInstanceUpdateComponent>;
    let service: TagInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [TagInstanceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TagInstanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagInstanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagInstanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TagInstance(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TagInstance();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

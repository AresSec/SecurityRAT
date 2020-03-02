import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SecurityRatTestModule } from '../../../test.module';
import { OptColumnTypeComponent } from 'app/entities/opt-column-type/opt-column-type.component';
import { OptColumnTypeService } from 'app/entities/opt-column-type/opt-column-type.service';
import { OptColumnType } from 'app/shared/model/opt-column-type.model';

describe('Component Tests', () => {
  describe('OptColumnType Management Component', () => {
    let comp: OptColumnTypeComponent;
    let fixture: ComponentFixture<OptColumnTypeComponent>;
    let service: OptColumnTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SecurityRatTestModule],
        declarations: [OptColumnTypeComponent]
      })
        .overrideTemplate(OptColumnTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OptColumnTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OptColumnTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OptColumnType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.optColumnTypes && comp.optColumnTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

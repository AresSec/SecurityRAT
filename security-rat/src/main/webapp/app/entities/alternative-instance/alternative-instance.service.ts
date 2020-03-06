import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAlternativeInstance } from 'app/shared/model/alternative-instance.model';

type EntityResponseType = HttpResponse<IAlternativeInstance>;
type EntityArrayResponseType = HttpResponse<IAlternativeInstance[]>;

@Injectable({ providedIn: 'root' })
export class AlternativeInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/alternative-instances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/alternative-instances';

  constructor(protected http: HttpClient) {}

  create(alternativeInstance: IAlternativeInstance): Observable<EntityResponseType> {
    return this.http.post<IAlternativeInstance>(this.resourceUrl, alternativeInstance, { observe: 'response' });
  }

  update(alternativeInstance: IAlternativeInstance): Observable<EntityResponseType> {
    return this.http.put<IAlternativeInstance>(this.resourceUrl, alternativeInstance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlternativeInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlternativeInstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlternativeInstance[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

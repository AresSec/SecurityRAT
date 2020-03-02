import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IOptColumnType } from 'app/shared/model/opt-column-type.model';

type EntityResponseType = HttpResponse<IOptColumnType>;
type EntityArrayResponseType = HttpResponse<IOptColumnType[]>;

@Injectable({ providedIn: 'root' })
export class OptColumnTypeService {
  public resourceUrl = SERVER_API_URL + 'api/opt-column-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/opt-column-types';

  constructor(protected http: HttpClient) {}

  create(optColumnType: IOptColumnType): Observable<EntityResponseType> {
    return this.http.post<IOptColumnType>(this.resourceUrl, optColumnType, { observe: 'response' });
  }

  update(optColumnType: IOptColumnType): Observable<EntityResponseType> {
    return this.http.put<IOptColumnType>(this.resourceUrl, optColumnType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOptColumnType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOptColumnType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOptColumnType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

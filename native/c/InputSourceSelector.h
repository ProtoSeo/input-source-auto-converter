//
//  InputSourceSelector.h
//  native
//
//  Created by 서승훈 on 1/11/24.
//

#ifndef InputSourceSelector_h
#define InputSourceSelector_h

#include <Carbon/Carbon.h>

TISInputSourceRef getInputSourceRef(char* sourceId);

void selectInputSource(char* sourceId);

#endif /* InputSourceSelector_h */

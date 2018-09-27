import store from '../store'

/*
    This will cehck to see if the user is authenticated or not.
*/
export default async function requireAuth (to, from, next) {
  /*
    Confirms the user has been loaded
    */
  if (!store.state.user.loaded) {
    /*
        If not, load the user
      */
    try {
      await store.dispatch('loadUser')
    } catch (err) {
    }
    if (store.state.user.loaded) {
      next()
    } else {
      next('/login')
    }
  } else {
    next()
  }
}

/*
 * ex: set ro:
 * DO NOT EDIT.
 * generated by smc (http://smc.sourceforge.net/)
 * from file : Turnstile.sm
 */

package com.fsryan.example.smcgradleplugin;


public class TurnstileContext
    extends statemap.FSMContext
{
//---------------------------------------------------------------
// Member methods.
//

    public TurnstileContext(Turnstile owner)
    {
        this (owner, MainMap.Locked);
    }

    public TurnstileContext(Turnstile owner, TurnstileState initState)
    {
        super (initState);

        _owner = owner;
    }

    @Override
    public void enterStartState()
    {
        getState().entry(this);
        return;
    }

    public void coin()
    {
        _transition = "coin";
        getState().coin(this);
        _transition = "";
        return;
    }

    public void pass()
    {
        _transition = "pass";
        getState().pass(this);
        _transition = "";
        return;
    }

    public TurnstileState getState()
        throws statemap.StateUndefinedException
    {
        if (_state == null)
        {
            throw(
                new statemap.StateUndefinedException());
        }

        return ((TurnstileState) _state);
    }

    protected Turnstile getOwner()
    {
        return (_owner);
    }

    public void setOwner(Turnstile owner)
    {
        if (owner == null)
        {
            throw (
                new NullPointerException(
                    "null owner"));
        }
        else
        {
            _owner = owner;
        }

        return;
    }

//---------------------------------------------------------------
// Member data.
//

    transient private Turnstile _owner;

    //-----------------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 1L;

//---------------------------------------------------------------
// Inner classes.
//

    public static abstract class TurnstileState
        extends statemap.State
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected TurnstileState(String name, int id)
        {
            super (name, id);
        }

        protected void entry(TurnstileContext context) {}
        protected void exit(TurnstileContext context) {}

        protected void coin(TurnstileContext context)
        {
            Default(context);
        }

        protected void pass(TurnstileContext context)
        {
            Default(context);
        }

        protected void Default(TurnstileContext context)
        {
            throw (
                new statemap.TransitionUndefinedException(
                    "State: " +
                    context.getState().getName() +
                    ", Transition: " +
                    context.getTransition()));
        }

    //-----------------------------------------------------------
    // Member data.
    //

        //-------------------------------------------------------
    // Constants.
    //

        private static final long serialVersionUID = 1L;
    }

    /* package */ static abstract class MainMap
    {
    //-----------------------------------------------------------
    // Member methods.
    //

    //-----------------------------------------------------------
    // Member data.
    //

        //-------------------------------------------------------
        // Constants.
        //

        public static final MainMap_Locked Locked =
            new MainMap_Locked("MainMap.Locked", 0);
        public static final MainMap_Unlocked Unlocked =
            new MainMap_Unlocked("MainMap.Unlocked", 1);
    }

    protected static class MainMap_Default
        extends TurnstileState
    {
    //-----------------------------------------------------------
    // Member methods.
    //

        protected MainMap_Default(String name, int id)
        {
            super (name, id);
        }

    //-----------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class MainMap_Locked
        extends MainMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private MainMap_Locked(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void coin(TurnstileContext context)
        {
            Turnstile ctxt = context.getOwner();

            (context.getState()).exit(context);
            context.clearState();
            try
            {
                ctxt.unlock();
            }
            finally
            {
                context.setState(MainMap.Unlocked);
                (context.getState()).entry(context);
            }

            return;
        }

        @Override
        protected void pass(TurnstileContext context)
        {
            Turnstile ctxt = context.getOwner();

            TurnstileState endState = context.getState();
            context.clearState();
            try
            {
                ctxt.alarm();
            }
            finally
            {
                context.setState(endState);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }

    private static final class MainMap_Unlocked
        extends MainMap_Default
    {
    //-------------------------------------------------------
    // Member methods.
    //

        private MainMap_Unlocked(String name, int id)
        {
            super (name, id);
        }

        @Override
        protected void coin(TurnstileContext context)
        {
            Turnstile ctxt = context.getOwner();

            TurnstileState endState = context.getState();
            context.clearState();
            try
            {
                ctxt.thankyou();
            }
            finally
            {
                context.setState(endState);
            }

            return;
        }

        @Override
        protected void pass(TurnstileContext context)
        {
            Turnstile ctxt = context.getOwner();

            (context.getState()).exit(context);
            context.clearState();
            try
            {
                ctxt.lock();
            }
            finally
            {
                context.setState(MainMap.Locked);
                (context.getState()).entry(context);
            }

            return;
        }

    //-------------------------------------------------------
    // Member data.
    //

        //---------------------------------------------------
        // Constants.
        //

        private static final long serialVersionUID = 1L;
    }
}

/*
 * Local variables:
 *  buffer-read-only: t
 * End:
 */